import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterPayload } from './register/register-payload';
import { BlogApi } from '../blog-api';
import { Observable } from 'rxjs';
import { LoginPayload } from './login/login-payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) {

   }

   register(registerPayload: RegisterPayload): Observable<any>{
     return this.httpClient.post(BlogApi.AUTH_API_REGISTER,registerPayload);
   }

   login(loginPayload: LoginPayload): Observable<any>{
    return this.httpClient.post(BlogApi.AUTH_API_LOGIN,loginPayload,{observe: 'response' });
  }
}
