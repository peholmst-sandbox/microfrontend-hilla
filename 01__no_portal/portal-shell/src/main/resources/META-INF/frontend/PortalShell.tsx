import {ReactNode, useEffect, useState} from "react";
import {AppLayout} from '@hilla/react-components/AppLayout.js';
import {DrawerToggle} from '@hilla/react-components/DrawerToggle.js';
import {Tabs} from '@hilla/react-components/Tabs.js';
import {Tab} from '@hilla/react-components/Tab.js';

import {PortalShellEndpoint} from 'Frontend/generated/endpoints.js';
import Frontend from 'Frontend/generated/org/vaadin/petter/hillamicro/portal/shell/endpoint/Frontend';

interface PortalShellProps {
    children: ReactNode;
    title?: string;
}

export default function PortalShell(props: PortalShellProps) {
    const initialFrontends: Frontend[] = [];
    const [frontends, setFrontends] = useState(initialFrontends);
    useEffect(() => {
        PortalShellEndpoint.getFrontends().then(result => {
            setFrontends(result);
        });
    }, []);

    return (
        <>
            <AppLayout>
                <DrawerToggle slot="navbar"></DrawerToggle>
                <h3 slot="navbar">
                    {props.title}
                </h3>
                <Tabs slot="drawer" orientation="vertical">
                    {frontends.map(frontend => (
                        <Tab>
                            <a tabIndex={-1} target={"_blank"} href={frontend.url}>
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
