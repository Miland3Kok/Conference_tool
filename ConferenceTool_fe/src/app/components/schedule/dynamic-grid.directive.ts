import {Directive, ElementRef, Input, Renderer2} from '@angular/core';

@Directive({
    selector: '[dynamicGrid]',
})
export class DynamicGridDirective {
    constructor(private el: ElementRef, private renderer: Renderer2) {
    }

    @Input() set dynamicGrid(rooms: number) {
        const gridStyle = `repeat(${rooms}, 1fr)`;


        let templateColumns = '[times] 7em [track-1-start] minmax(0, 1fr) ';

        for (let i = 1; i < rooms; i++) {
            templateColumns += `[track-${i}-end track-${i + 1}-start] minmax(0, 1fr) `;
        }

        templateColumns += `[track-${rooms}-end]`;

        console.log(templateColumns);

        this.renderer.setStyle(this.el.nativeElement, 'grid-template-columns', templateColumns);

    }

}
