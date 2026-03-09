import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent {
  registerForm!: FormGroup;
  error: string | null = null;
  loading: boolean = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.registerForm = new FormGroup({
      userName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl('', [Validators.required]),
    });
  }

  submit(): void {
    this.error = null;
    if (this.registerForm.value.password !== this.registerForm.value.confirmPassword) {
      this.error = "Passwords do not match!";
      return;
    }

    this.loading = true;
    this.userService.registerUser(this.registerForm.value).subscribe({
      next: (value) => {
        this.loading = false;
        console.log("Registered:", value);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.loading = false;
        this.error = "Registration failed. Username might be taken.";
        console.error("Register error:", err);
      }
    });
  }
}
