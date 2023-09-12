import federation from '@originjs/vite-plugin-federation';

import {UserConfigFn} from 'vite';
import {overrideVaadinConfig} from './vite.generated';

const customConfig: UserConfigFn = (env) => ({
    build: {
        target: 'esnext',
    },
    plugins: [
        federation({
            name: 'hillamicro-hello-app',
            filename: 'hello-app-container.js',
            exposes: {
                './hello-app': './frontend/hello-app.ts',
            },
            remotes: {
                components: '/_apps/components/assets/components-container.js',
                portal: '/VAADIN/build/portal-container.js',
            },
            shared: {
                'lit-html': {version: '2.7.6'},
                'lit': {version: '2.7.6'},
                'lit-element': {version: '2.7.6'},
            },
        }),
    ],
});

export default overrideVaadinConfig(customConfig);
