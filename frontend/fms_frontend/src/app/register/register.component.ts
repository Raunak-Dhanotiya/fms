import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from '../services/auth.service';

declare var $: any;
@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    registerForm: UntypedFormGroup;

    submitted = false;
    isRegistrationFailed = false;
    registrationFailedMsg = '';
    showPassword = false;
    showConfirmPassword = false;

    roles = [
        'Admin',
        'Manager',
        'User'
    ];

    constructor(
        private fb: UntypedFormBuilder,
        private router: Router,
        private authService: AuthService,
        private spinner: NgxSpinnerService
    ) {

        this.registerForm = this.fb.group({
            fullName: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(6)]],
            confirmPassword: ['', Validators.required],
            role: ['', Validators.required]
        });

    }

    ngOnInit(): void {
    }

    // Convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    togglePassword() {
        this.showPassword = !this.showPassword;
    }

    toggleConfirmPassword() {
        this.showConfirmPassword = !this.showConfirmPassword;
    }

    onSubmit() {

        this.submitted = true;
        this.isRegistrationFailed = false;
        this.registrationFailedMsg = '';

        if (this.registerForm.invalid) {
            return;
        }

        // Password match validation
        if (this.registerForm.value.password !== this.registerForm.value.confirmPassword) {
            this.isRegistrationFailed = true;
            this.registrationFailedMsg = 'Passwords do not match.';
            return;
        }

        this.spinner.show();

        const { confirmPassword, ...registerPayload } = this.registerForm.value;

        this.authService.register(registerPayload)
            .subscribe({

                next: (res: any) => {

                    this.spinner.hide();
                    alert('Registration Successful');

                    this.router.navigate(['/login']);
                },

                error: (err) => {

                    this.spinner.hide();
                    this.isRegistrationFailed = true;

                    if (err.status === 400) {
                        this.registrationFailedMsg = typeof err.error === 'string'
                            ? err.error
                            : 'Username already exists.';
                    } else if (err.status === 0) {
                        this.registrationFailedMsg = 'Unable to connect to server. Please try again later.';
                    } else {
                        this.registrationFailedMsg = 'Registration failed. Please try again.';
                    }
                }

            });
    }

    navigateLogin() {

        this.router.navigate(['/login']);
    }

}
