import { NgModule } from '@angular/core';
import {DynamicGridDirective} from "./dynamic-grid.directive";

@NgModule({
    declarations: [
        DynamicGridDirective,
    ],
    exports: [
        DynamicGridDirective,
    ],
})
export class ScheduleModule { }