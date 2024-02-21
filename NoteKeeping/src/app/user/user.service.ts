import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../shared/constants/app.constant';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  registerUser(body: any): Observable<any> {
    return this.http.post<any>(APP_CONSTANTS.BACKEND_URL+'register' , body);
  }

  login(body:any):Observable<any>{
    return this.http.post(APP_CONSTANTS.BACKEND_URL+'login' , body)
  }
}
