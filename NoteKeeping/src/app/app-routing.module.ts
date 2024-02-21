import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRegisterComponent } from './user/user-register/user-register.component';
import { UserLoginComponent } from './user/user-login/user-login.component';
import { CreateNoteComponent } from './note/create-note/create-note.component';

const routes: Routes = [
  {
   path:'register',
   component: UserRegisterComponent
  },
  {
    path:'login',
    component: UserLoginComponent
  },
  {
    path:'note',
    component:CreateNoteComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
