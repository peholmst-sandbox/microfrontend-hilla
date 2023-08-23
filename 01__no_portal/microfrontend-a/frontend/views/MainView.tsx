import {Button} from "@hilla/react-components/Button.js";
import {TextField} from "@hilla/react-components/TextField.js";
import {useState} from "react";

import {PortalShellEndpoint} from 'Frontend/generated/endpoints.js';
import NotificationPriority
    from "Frontend/generated/org/vaadin/petter/hillamicro/portal/shell/endpoint/NotificationPriority";


export default function MainView() {
    const [name, setName] = useState("");

    return (
        <>
            <TextField
                label="Your name"
                onValueChanged={(e) => {
                    setName(e.detail.value);
                }}
            />
            <Button
                onClick={async () => {
                    await PortalShellEndpoint.pushNotification(NotificationPriority.INFO, `Frontend A wishes it to be known that it is currently being used by ${name}.`);
                }}
            >
                Say hello
            </Button>
        </>
    );
}
