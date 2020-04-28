import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { AuthService } from './auth/auth.service';

@Injectable()
export class HttpClientInterceptor implements HttpInterceptor {

  private token:string;  
  constructor(private authServie:AuthService) {

  }

  intercept(req: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    this.token = this.authServie.getToken()+'';
    console.log('jwt token ' + this.token);
    if (this.token) {
      const cloned = req.clone({
        headers: req.headers.set("Authorization",this.token)
      });

      return next.handle(cloned);
    }
    else {
      return next.handle(req);
    }
  }
}