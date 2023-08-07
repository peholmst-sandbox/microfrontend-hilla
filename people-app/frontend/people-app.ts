import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

//@ts-ignore
import('components/all.js');

@customElement('people-app')
export class PeopleApp extends LitElement {

    render() {
        return html`
            <h1>People</h1>
        `;
    }
}