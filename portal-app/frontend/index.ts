import { Router } from '@vaadin/router';
import { routes } from './routes';
import { appStore } from './stores/app-store';

import('./application-tracker');

export const router = new Router(document.querySelector('#outlet'));
router.setRoutes(routes, document.location.pathname !== '/');

window.addEventListener('vaadin-router-location-changed', (e) => {
    appStore.setLocation((e as CustomEvent).detail.location);
    const title = appStore.currentViewTitle;
    if (title) {
        document.title = title + ' | ' + appStore.applicationName;
    } else {
        document.title = appStore.applicationName;
    }
});
