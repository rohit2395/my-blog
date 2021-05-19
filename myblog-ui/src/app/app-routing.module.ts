import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent} from './auth/register/register.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { LoginComponent } from './auth/login/login.component';
import { AddPostComponent } from './add-post/add-post.component';
import { ProfileComponent } from './profile/profile.component';
import { PostComponent } from './post/post.component';
import { AuthGuard } from './auth.guard';


export const routes: Routes = [
  {
    path:'',
    children: [
      // { path: '', redirectTo: '/home', pathMatch: 'full'},
      { path: 'home', component: HomeComponent ,canActivate:[AuthGuard]},
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
      },
      { path: 'register', component: RegisterComponent,pathMatch: 'full'},
      { path: 'login', component: LoginComponent ,pathMatch: 'full'},
      { path: 'add-post', component: AddPostComponent,canActivate:[AuthGuard] ,pathMatch: 'full'},
      { path: 'posts/:id', component: PostComponent,canActivate:[AuthGuard],pathMatch: 'full'},
      { path: 'profile', component: ProfileComponent,canActivate:[AuthGuard] ,pathMatch: 'full'},
      {path: '**', component: NotfoundComponent,canActivate:[AuthGuard],pathMatch: 'full'},
      // {path: '404', component: NotfoundComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
