import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { APP_CONSTANTS } from '../shared/constants/app.constant';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  constructor(private http: HttpClient) { }

  getNotes(): Observable<any[]> {
    return this.http.get<any[]>(APP_CONSTANTS.BACKEND_URL + 'note', { withCredentials: true });
  }

  deleteNote(noteId: number): Observable<any> {
    return this.http.delete(APP_CONSTANTS.BACKEND_URL + 'note/' + noteId, { withCredentials: true });
  }

  updateNote(noteId: number, body: any): Observable<any> {
    return this.http.put(APP_CONSTANTS.BACKEND_URL + 'note/' + noteId, body, { withCredentials: true });
  }

  addNote(body: any): Observable<any> {
    return this.http.post(APP_CONSTANTS.BACKEND_URL + 'note', body, { withCredentials: true });
  }
}
