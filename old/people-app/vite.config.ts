import federation from '@originjs/vite-plugin-federation';

import {UserConfigFn} from 'vite';
import {overrideVaadinConfig} from './vite.generated';

const customConfig: UserConfigFn = (env) => ({
    build: {
        target: 'esnext',
    },
    plugins: [
        federation({
            name: 'hillamicro-people-app',
            filename: 'people-app-container.js',
            exposes: {
                './people-app': './frontend/people-app.ts',
            },
            remotes: {
                components: '/_apps/components/assets/components-container.js'
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
