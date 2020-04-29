import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterPayload } from './register/register-payload';
import { BlogApi } from '../blog-api';
import { Observable } from 'rxjs';
import { LoginPayload } from './login/login-payload';
import * as jwt_decode from 'jwt-decode';
import { PostPayload } from '../add-post/post-payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isLoggedIn: Boolean;

  public username: String;
  public token: String;
  public roles: String; 

  constructor(private httpClient: HttpClient) {

   }

   testConnetion():Observable<any>{
    return this.httpClient.get(BlogApi.AUTH_API_REGISTER,{observe: 'response' });
   }

   register(registerPayload: RegisterPayload): Observable<any>{
     return this.httpClient.post(BlogApi.AUTH_API_REGISTER,registerPayload,{observe: 'response' });
   }

   login(loginPayload: LoginPayload): Observable<any>{
    return this.httpClient.post(BlogApi.AUTH_API_LOGIN,loginPayload,{observe: 'response' });
  }

  isAuthenticated(): Boolean{
      if(this.username == null){
        this.username = localStorage.getItem('username'); 
      }
      if(this.token == null){
        this.token = localStorage.getItem('auth-token');
      }
      if(this.roles == null){
        this.roles = localStorage.getItem('user-role');
      }
      if(this.username != null && this.token != null){
        var decoded = jwt_decode(this.token); 
        const now = Math.round((new Date()).getTime() / 1000);
        if(now > decoded.exp){
          console.error('Token is expired');
          this.logout();
          this.isLoggedIn = false; 
        }
        this.isLoggedIn = true;
      }else{
        this.isLoggedIn = false;
      }   
    return this.isLoggedIn;
  }

  getToken():String{
    return this.token;
  }
  getUserName():String{
    return this.username;
  }
  getUserRoles(): String{
    return this.roles;
  }

  logout(): void{
    localStorage.removeItem('username');
    localStorage.removeItem('auth-token');   
    localStorage.removeItem('user-role');
  }
}
