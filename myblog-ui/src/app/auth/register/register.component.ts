import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RegisterPayload } from './register-payload';
import { AuthService } from '../auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  registerForm: FormGroup;

  registerPayload: RegisterPayload;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private _snackBar: MatSnackBar,private router: Router) {
    this.registerForm = this.formBuilder.group({
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    });

    this.registerPayload = {
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    };
  }

  ngOnInit(): void {
  }


  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass:'panel-class'
    });
  }

  onSubmit(): void {
    this.registerPayload.username = this.registerForm.get('username').value;
    this.registerPayload.email = this.registerForm.get('email').value;
    this.registerPayload.password = this.registerForm.get('password').value;
    this.registerPayload.confirmPassword = this.registerForm.get('confirmPassword').value;
    this.authService.register(this.registerPayload).subscribe(data => {
      console.log(data);
      if(data.status == '201'){
        this.openSnackBar(data.message,'Close');
        this.router.navigateByUrl('/home');
        // this.dialogRef.close();
      }
      else
        this.openSnackBar('User registration failed','Close');
    }, error => {
      console.log(error.error.message,error);
      this.openSnackBar(error.error.message,'Close');
    });
  }
}
  
// use this for pop up
// @Component({
//   template: ''
// })
// export class RegisterDialogEntryComponent {
//   constructor(public dialog: MatDialog, private router: Router,
//     private route: ActivatedRoute) {
//     this.openDialog();
//   }
//   openDialog(): void {
//     const dialogRef = this.dialog.open(RegisterComponent, {
//       width: '500px',
//     });
//     dialogRef.afterClosed().subscribe(result => {
//       this.router.navigate(['../'], { relativeTo: this.route });
//     });
//   }
// }
