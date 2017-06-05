import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {CookieService} from "angular2-cookie/services/cookies.service";
import {AuthService} from "../auth.service";

@Component({
  template: ``
})
export class SocialComponent implements OnInit {

  private socialCookieName = 'social-authentication';

  constructor(private router: Router,
              private cookieService: CookieService,
              private authService:AuthService) {
  }

  ngOnInit(): void {
    let token = this.cookieService.get(this.socialCookieName);
    if (token) {
      this.authenticate(token);
    } else {
      this.router.navigate(['/error']);
    }
  }

  private authenticate(token: string): void {
    localStorage.setItem('currentUser', JSON.stringify({token: token}));
    this.cookieService.remove(this.socialCookieName);
    this.authService.token = token;
    this.authService.decodeTokenData();
    this.router.navigate(['/']);
  }
}
