import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../shared/constants/app.constant';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  constructor(private http: HttpClient) { }

  addNote(body: any ): Observable<any> {
    return this.http.post(APP_CONSTANTS.BACKEND_URL+'note' , body , {withCredentials: true});
  }
}
