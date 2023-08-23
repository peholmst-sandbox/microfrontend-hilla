import {router} from "Frontend/routes.js";
import {RouterProvider} from "react-router-dom";
import PortalShell from "Frontend/generated/jar-resources/PortalShell";

export default function App() {
    return (<>
        <PortalShell title={"Frontend B"}>
            <RouterProvider router={router}/>
        </PortalShell>
    </>);
}
