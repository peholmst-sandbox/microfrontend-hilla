import federation from '@originjs/vite-plugin-federation';

export default {
  root: 'frontend',
  base: '',
  build: {
    target: 'esnext',
    outDir: '../target/classes/public',
  },
  plugins: [
    federation({
      name: 'hillamicro-components',
      filename: 'components-container.js',
      exposes: {
        './all.js': './frontend/components.js',
      },
      shared: {
        'lit-html': { version: '2.7.6' },
        'lit': { version: '2.7.6' },
        'lit-element': { version: '2.7.6' },
      },
    }),
  ],
};