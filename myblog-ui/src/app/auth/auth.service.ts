import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterPayload } from './register/register-payload';
import { BlogApi } from '../blog-api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) {

   }

   register(registerPayload: RegisterPayload): Observable<any>{
     return this.httpClient.post(BlogApi.AUTH_API_REGISTER,registerPayload);
   }
}
