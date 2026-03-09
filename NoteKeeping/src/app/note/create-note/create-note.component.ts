import { Component, OnInit } from '@angular/core';
import { NoteService } from '../note.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-note',
  templateUrl: './create-note.component.html',
  styleUrls: ['./create-note.component.css']
})
export class CreateNoteComponent implements OnInit {
  createnote !: FormGroup;
  notes: any[] = [];
  loadingNotes: boolean = false;
  isEditMode: boolean = false;
  editingNoteId: number | null = null;
  categories: string[] = ['Personal', 'Work', 'Urgent', 'Idea'];
  searchText: string = '';
  sortBy: string = 'Recent';
  isDarkMode: boolean = true;
  imageUrl: string | null = null;

  constructor(
    private noteservice: NoteService,
    private cookieService: CookieService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.fetchNotes();
  }

  fetchNotes() {
    this.loadingNotes = true;
    this.noteservice.getNotes().subscribe({
      next: (data) => {
        this.notes = data.reverse();
        this.loadingNotes = false;
      },
      error: (err) => {
        console.error("Fetch notes error:", err);
        this.loadingNotes = false;
      }
    });
  }

  get filteredNotes() {
    let result = [...this.notes];

    // 1. Filter
    if (this.searchText) {
      const filter = this.searchText.toLowerCase();
      result = result.filter(note =>
        (note.title?.toLowerCase().includes(filter)) ||
        (note.descrpition?.toLowerCase().includes(filter)) ||
        (note.description?.toLowerCase().includes(filter)) ||
        (note.category?.toLowerCase().includes(filter))
      );
    }

    // 2. Sort
    return result.sort((a, b) => {
      const dateA = new Date(a.lastModifiedDate || a.createdDate).getTime();
      const dateB = new Date(b.lastModifiedDate || b.createdDate).getTime();

      if (this.sortBy === 'Recent') return dateB - dateA;
      if (this.sortBy === 'Oldest') return dateA - dateB;
      if (this.sortBy === 'Alphabetical') {
        const titleA = a.title || '';
        const titleB = b.title || '';
        return titleA.localeCompare(titleB);
      }
      return 0;
    });
  }

  initForm() {
    this.createnote = new FormGroup({
      title: new FormControl('', [Validators.required]),
      descrpition: new FormControl('', [Validators.required]),
      category: new FormControl('Personal')
    });
  }

  prepareEdit(note: any) {
    this.isEditMode = true;
    this.editingNoteId = note.noteId || note.noteID;
    this.createnote.patchValue({
      title: note.title,
      descrpition: note.description || note.descrpition,
      category: note.category || 'Personal'
    });
    this.imageUrl = note.image_url;
    // Smooth scroll to top to see the form
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancelEdit() {
    this.isEditMode = false;
    this.editingNoteId = null;
    this.createnote.reset();
  }

  submit(): void {
    if (this.createnote.invalid) return;

    const body = {
      ...this.createnote.value,
      CreatedByUser: this.cookieService.get('userID'),
      imageUrl: this.imageUrl
    };

    if (this.isEditMode && this.editingNoteId) {
      this.noteservice.updateNote(this.editingNoteId, body).subscribe({
        next: (res) => {
          console.log("Note updated:", res);
          this.resetAfterSubmit();
        },
        error: (err) => {
          console.error("Update error:", err);
          alert("Update failed: " + (err.error?.error || err.error?.status || err.message));
        }
      });
    } else {
      this.noteservice.addNote(body).subscribe({
        next: (value) => {
          console.log("Note saved:", value);
          this.resetAfterSubmit();
        },
        error: (err) => {
          console.error("POST /api/note failed:", err.status, err.error);
          alert("Save failed: " + (err.error?.error || err.error?.status || err.message));
        }
      });
    }
  }

  private resetAfterSubmit() {
    this.isEditMode = false;
    this.editingNoteId = null;
    this.imageUrl = null;
    this.createnote.reset();
    this.fetchNotes();
  }

  deleteNote(noteId: number) {
    if (confirm("Are you sure you want to delete this note?")) {
      this.noteservice.deleteNote(noteId).subscribe({
        next: (res) => {
          console.log("Note deleted:", res);
          this.fetchNotes();
        },
        error: (err) => console.error("Delete error:", err)
      });
    }
  }

  getCategoryColor(category: string): string {
    switch (category) {
      case 'Urgent': return '#ef4444';
      case 'Work': return '#3b82f6';
      case 'Idea': return '#f59e0b';
      default: return '#10b981';
    }
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imageUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    if (this.isDarkMode) {
      document.body.classList.remove('light-mode');
    } else {
      document.body.classList.add('light-mode');
    }
  }

  logout() {
    this.cookieService.deleteAll('/');
    this.router.navigate(['/login']);
  }
}
