import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BlogApi } from '../blog-api';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  
  constructor(private httpClient: HttpClient) { }

  
  getPost(paramLink:Number):Observable<any>{
    var url = BlogApi.POST_API_GET_POST + "/" + paramLink;
    return this.httpClient.get(url,{observe: 'response' });
  }
}
