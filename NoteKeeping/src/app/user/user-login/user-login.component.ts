import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent {
  loginForm!: FormGroup
  error: string | null = null;
  loading: boolean = false;

  constructor(private userService: UserService, private router: Router, private cookieService: CookieService) { }

  ngOnInit(): void {
    this.LoginForm()
  }
  LoginForm(): void {
    this.loginForm = new FormGroup({
      userName: new FormControl(),
      password: new FormControl(),
    })
  }
  submit(): void {
    this.loading = true;
    this.error = null;
    console.log(this.loginForm.value)

    this.userService.login(this.loginForm.value).subscribe({
      next: (value) => {
        this.loading = false;
        console.log("Login result:", value);
        if (value && value.validYN == 1) {
          this.cookieService.set("token", value.token, { path: '/' });
          this.cookieService.set("userName", value.userName, { path: '/' });
          this.cookieService.set("userID", value.userID.toString(), { path: '/' });
          this.cookieService.set("CreatedByUser", value.userID.toString(), { path: '/' });
          this.router.navigate(['/note']);
        } else {
          this.error = "Invalid username or password";
        }
      },
      error: (err) => {
        this.loading = false;
        this.error = "Connection failed. Please check your backend.";
        console.error("Login component error:", err);
      }
    })
  }
}
