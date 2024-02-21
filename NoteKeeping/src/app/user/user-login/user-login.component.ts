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
  loginForm!:FormGroup
  constructor(private userService : UserService , private router: Router , private cookieService : CookieService){}

  ngOnInit():void{
    this.LoginForm()
  }
  LoginForm():void{
    this.loginForm = new FormGroup({
      userName: new FormControl(),
      password: new FormControl(),
    })
  }
  submit():void{
      console.log(this.loginForm.value)
      this.userService.login(this.loginForm.value).subscribe((value)=>{
       console.log("Logined!!")
       console.log(value);
       if(value.validYN == 1)
       this.cookieService.set("token" , value.token)
        this.cookieService.set("userName" , value.userName)
        this.cookieService.set("userID" , value.userID)
        this.cookieService.set("CreatedByUser" , value.userID)
        console.log(this.cookieService.get('CreatedByUser'))
        this.router.navigate(['/note'])
      })
  }
}
