import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('hello-app')
export class HelloApp extends LitElement {

    render() {
        return html`
            <h1>Hello World!</h1>
        `;
    }

}