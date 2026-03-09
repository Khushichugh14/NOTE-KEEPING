import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { CookieService } from 'ngx-cookie-service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  user: any = {};
  loading: boolean = true;
  saving: boolean = false;
  userId: number;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private userService: UserService,
    private cookieService: CookieService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.userId = parseInt(this.cookieService.get('userID'));
  }

  ngOnInit(): void {
    this.initForm();
    this.fetchProfile();
  }

  initForm() {
    this.profileForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.minLength(6)]],
      confirmPassword: ['']
    });
  }

  fetchProfile() {
    this.userService.getUserProfile(this.userId).subscribe({
      next: (data) => {
        this.user = data;
        this.profileForm.patchValue({ email: data.email });
        this.loading = false;
      },
      error: (err) => {
        console.error("Profile fetch error:", err);
        this.errorMessage = 'Could not load profile. Please try again.';
        this.loading = false;
      }
    });
  }

  showSuccess(msg: string) {
    this.successMessage = msg;
    this.errorMessage = '';
    setTimeout(() => this.successMessage = '', 4000);
  }

  showError(msg: string) {
    this.errorMessage = msg;
    this.successMessage = '';
    setTimeout(() => this.errorMessage = '', 4000);
  }

  updateProfile() {
    if (this.profileForm.invalid) return;

    const { email, password, confirmPassword } = this.profileForm.value;

    if (password && password !== confirmPassword) {
      this.showError('Passwords do not match!');
      return;
    }

    this.saving = true;
    this.userService.updateProfile(this.userId, { email, password }).subscribe({
      next: (res) => {
        this.showSuccess('Profile updated successfully! ✓');
        this.saving = false;
        this.user.email = email;
        this.profileForm.get('password')?.reset();
        this.profileForm.get('confirmPassword')?.reset();
      },
      error: (err) => {
        this.showError('Update failed: ' + (err.error?.status || err.message));
        this.saving = false;
      }
    });
  }

  logout() {
    this.cookieService.deleteAll('/');
    this.router.navigate(['/login']);
  }
}
