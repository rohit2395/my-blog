import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RegisterPayload } from './register-payload';
import { AuthService } from '../auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { BlogConstants } from 'src/app/constant';
import * as jwt_decode from 'jwt-decode';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  registerForm: FormGroup;

  registerPayload: RegisterPayload;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private _snackBar: MatSnackBar, private router: Router) {
    this.registerForm = this.formBuilder.group({
      name: '',
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    });

    this.registerPayload = {
      name: '',
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    };
  }

  ngOnInit(): void {
    if (this.authService.isAuthenticated())
      this.router.navigateByUrl('/home');
  }


  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass: 'panel-class'
    });
  }

  onSubmit(): void {
    this.registerPayload.name = this.registerForm.get('name').value;
    this.registerPayload.username = this.registerForm.get('username').value;
    this.registerPayload.email = this.registerForm.get('email').value;
    this.registerPayload.password = this.registerForm.get('password').value;
    this.registerPayload.confirmPassword = this.registerForm.get('confirmPassword').value;
    this.authService.register(this.registerPayload).subscribe(response => {
      console.log(response);
      if (response.status == '201') {
        this.openSnackBar(response.body.message, 'Close');
        response.headers.keys();

        var token = response.headers.headers.get('auth-token')[0];
        console.log('Storing data from token in local storage');
        localStorage.setItem('auth-token', token);
        localStorage.setItem('username', this.registerPayload.username + '');
        var rolesArray = jwt_decode(token).role;
        var rolesString = '';
        for (let i = 0; i < rolesArray.length; i++) {
          rolesString += rolesArray[i].authority + ",";
        }
        if (rolesString != '') {
          rolesString = rolesString.slice(0, -1);
        }
        localStorage.setItem('user-role', rolesString);
        this.authService.roles = rolesString;
        this.router.navigateByUrl('/home');
        // this.dialogRef.close();
      }
      else
        this.openSnackBar('User registration failed', 'Close');
    }, error => {
      console.log(error);
      if (error.status == 500) {
        this.openSnackBar(error.error.message, 'Close');
      } else {
        this.openSnackBar(BlogConstants.SOMETHING_WENT_WRONG, 'Close');
      }
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
