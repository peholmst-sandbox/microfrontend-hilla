import type {Route} from '@vaadin/router';
import './views/main-layout';
import {appStore} from './stores/app-store';

export type ViewRoute = Route & {
    title?: string;
    icon?: string;
    children?: ViewRoute[];
};

export const views: ViewRoute[] = [
    {
        path: '(.*)',
        action: async (_context, _command) => {
            const pathname = _context.pathname;
            const application = appStore.applications.find((app) => app.path === pathname);
            if (application) {
                console.debug('Trying to load application', application);
                const applicationContainer = await import(/* @vite-ignore */ application.importPath);
                // These methods are basically the same as in Webpack Module Federation.
                const scope = (window as any)?.__federation_shared__?.default || {};
                console.debug('Application container loaded, initializing using scope', scope);
                await applicationContainer.init(scope);
                console.debug(`Application container initialized, loading module ${application.moduleName}`);
                await applicationContainer.get(application.moduleName);
                console.debug(`Module loaded, returning application route tag <${application.tag}>`);
                return _command.component(application.tag);
            } else {
                console.error(`Could not find an application corresponding to path '${pathname}'`);
                return undefined;
            }
        },
    },
];

export const routes: Route[] = [
    {
        path: 'login',
        component: 'login-view',
        action: async (_context, _command) => {
            await import('./views/login/login-view.js');
            return;
        },
    },
    {
        path: '',
        component: 'main-layout',
        children: views,
    },
];
