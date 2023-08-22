import {Button} from "@hilla/react-components/Button.js";
import {TextField} from "@hilla/react-components/TextField.js";
import {useState} from "react";

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
                }}
            >
                Say hello
            </Button>
        </>
    );
}
