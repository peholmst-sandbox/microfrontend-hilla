import ApplicationInfo from '../generated/org/vaadin/petter/hillamicro/portal/ApplicationInfo'
import {RouterLocation} from '@vaadin/router';
import {makeAutoObservable} from 'mobx';

export class AppStore {

    applicationName = 'Hillamicro';

    location = '';

    currentViewTitle = '';

    applications: ApplicationInfo[] = [];

    constructor() {
        makeAutoObservable(this);
    }

    setLocation(location: RouterLocation) {
        if (location.route) {
            this.location = location.route.path;
        } else if (location.pathname.startsWith(location.baseUrl)) {
            this.location = location.pathname.substring(location.baseUrl.length);
        } else {
            this.location = location.pathname;
        }
        this.currentViewTitle = this.applications.find((app) => app.path === location.pathname)?.title || '';
    }

    setApplications(applications: ApplicationInfo[]) {
        this.applications = applications;
        console.debug(`Using applications ${JSON.stringify(applications)}`);
    }
}

export const appStore = new AppStore();