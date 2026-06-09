import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RegisterRoutingModule } from './register.routing.module';
import { NgxSpinnerModule } from 'ngx-spinner';

@NgModule({
    declarations: [
        RegisterComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        RegisterRoutingModule,
        NgxSpinnerModule
    ]
})
export class RegisterModule { }
