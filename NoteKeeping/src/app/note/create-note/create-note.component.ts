import { Component } from '@angular/core';
import { NoteService } from '../note.service';
import { FormControl, FormGroup } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-create-note',
  templateUrl: './create-note.component.html',
  styleUrls: ['./create-note.component.css']
})
export class CreateNoteComponent {
  createnote !: FormGroup


  constructor(private noteservice: NoteService , private cookieService : CookieService){}

  ngOnInit():void{
    this.createNote()
  }
  createNote() {
    this.createnote = new FormGroup ({
      title : new FormControl(),
      descrpition : new FormControl()
      // CreatedByUser :new FormControl()
    })
  }

  submit():void{
    // this.createnote.get('CreatedByUser').setValue(this.cookieService.get(userID))
    console.log(this.createnote.value)
    this.noteservice.addNote(this.createnote.value).subscribe((value) => {
      console.log("The note was created!")
      console.log(value)
    });
}
}
