import {ReactNode, useEffect, useState} from "react";
import {AppLayout} from '@hilla/react-components/AppLayout.js';
import {DrawerToggle} from '@hilla/react-components/DrawerToggle.js';
import {Tabs} from '@hilla/react-components/Tabs.js';
import {Tab} from '@hilla/react-components/Tab.js';
import {Subscription} from '@hilla/frontend';
import {Notification} from '@hilla/react-components/Notification.js';

import {PortalShellEndpoint} from 'Frontend/generated/endpoints.js';
import Frontend from 'Frontend/generated/org/vaadin/petter/hillamicro/portal/shell/endpoint/Frontend';
import FrontendNotification
    from 'Frontend/generated/org/vaadin/petter/hillamicro/portal/shell/endpoint/FrontendNotification';

interface PortalShellProps {
    children: ReactNode;
    title?: string;
}

export default function PortalShell(props: PortalShellProps) {
    const initialFrontends: Frontend[] = [];
    const [frontends, setFrontends] = useState(initialFrontends);
    const [selfFrontendId, setSelfFrontendId] = useState("");
    const [subscription, setSubscription] = useState<Subscription<FrontendNotification> | undefined>();
    const [notification, setNotification] = useState<FrontendNotification | undefined>();

    useEffect(() => {
        PortalShellEndpoint.getFrontends().then(frontends => {
            setFrontends(frontends);
        });
        PortalShellEndpoint.getSelf().then(self => {
            setSelfFrontendId(self.frontendId);
            window.name = self.frontendId;
        });
        setSubscription(PortalShellEndpoint.getNotifications().onNext((notification) => {
            setNotification(notification);
        }));
        return () => {
            if (subscription) {
                subscription.cancel();
                setSubscription(undefined);
            }
        };
    }, []);

    return (
        <>
            <Notification position={"middle"} opened={notification !== undefined}
                          onOpenedChanged={(event) => {
                              if (!event.detail.value) setNotification(undefined);
                          }}>
                <div>{notification?.message} <a href={notification?.url} target={notification?.frontendId}>More...</a>
                </div>
            </Notification>
            <AppLayout>
                <DrawerToggle slot="navbar"></DrawerToggle>
                <h3 slot="navbar">
                    {props.title}
                </h3>
                <Tabs slot="drawer" orientation="vertical">
                    {frontends.map(frontend => (
                        <Tab selected={frontend.frontendId === selfFrontendId}>
                            <a tabIndex={-1} target={frontend.frontendId} href={frontend.url}>
                                <span>{frontend.title}</span>
                            </a>
                        </Tab>
                    ))}
                </Tabs>
                {props.children}
            </AppLayout>
        </>
    );
}
