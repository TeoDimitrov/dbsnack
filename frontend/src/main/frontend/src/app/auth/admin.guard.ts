import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {JwtHelper} from "angular2-jwt/angular2-jwt";
import {AuthService} from "./auth.service";

@Injectable()
export class AdminGuard implements CanActivate {


  constructor(private router: Router, private authService:AuthService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if(this.authService.isRoleAdmin()){
      return true;
    }

    this.router.navigate(['login'], { queryParams: { returnUrl: state.url }});
    return false;
  }
}
