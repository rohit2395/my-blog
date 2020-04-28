import { Injectable } from '@angular/core';
import { PostPayload } from './post-payload';
import { HttpClient } from '@angular/common/http';
import { BlogApi } from '../blog-api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddPostService {

  constructor(private httpClient: HttpClient) { }

  
  addPost(postPayload: PostPayload):Observable<any>{
    return this.httpClient.post(BlogApi.POST_API_ADD,postPayload);
  }
}
