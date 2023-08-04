import federation from '@originjs/vite-plugin-federation';

import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

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
      shared: {
        '@vaadin/vaadin-lumo-styles': {},
         'lit-html': {},
         'lit': {},
         'lit-element': {},
      },
    }),
  ],
});

export default overrideVaadinConfig(customConfig);
