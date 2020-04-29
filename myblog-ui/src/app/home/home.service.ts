import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BlogApi } from '../blog-api';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private httpClient: HttpClient) { }

  
  getAllPosts():Observable<any>{
    return this.httpClient.get(BlogApi.POST_API_GET_ALL,{observe: 'response' });
  }
}
