import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent {
  registerForm!: FormGroup

  constructor(private userService: UserService){}

  createForm():void{
    this.registerForm = new FormGroup({
      userName: new FormControl(),
      email: new FormControl(),
       password: new FormControl(),
      confirmPassword: new FormControl(),
    })
  }
  ngOnInit(): void {
    this.createForm()
  }

  submit(): void {
    this.userService.registerUser(this.registerForm.value).subscribe((value)=>{
      console.log("Registered!!!")
    })
  }
}
