import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserPwdReset } from '../model/user-pwd-reset.model';
import { AuthService } from '../services/auth.service';
import { SharedService } from '../services/shared.service';
import { TokenStorageService } from '../services/tokenStorage.service';
import { UsersService } from '../services/users.service';

declare var $: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

  loginForm: UntypedFormGroup;
  resetForm:UntypedFormGroup;
  error:any= { errorTitle: '', errorDesc: '' };
  isLoggedIn :boolean= false;
  isLoginFailed :boolean= false;
  title:string = 'appBootstrap';
  isPasswordResetChecked:boolean=false;
  resetMsg:string="";
  resetErrorMsg:string="";
  isEmailSent:boolean=false;
  
  loginFailedMsg="";

  // Declare private variables
  constructor(private fb: UntypedFormBuilder, 
    private authService: AuthService, 
    private router: Router,
    private tokenStorage: TokenStorageService,
    private ss: SharedService,
    private userSrv:UsersService,
    private spinner: NgxSpinnerService,
    ) { 
      
      // Initialization of Reactive Form elements
    this.loginForm = this.fb.group({
      email: ['',[Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    this.resetForm = this.fb.group({
      userEmail: ['', [Validators.required,Validators.email]],
      
    });
    }

  ngOnInit() {
    setTimeout(() => {
      this.ss.change(this.authService.isLoggedIn());
    });
    $(document).ready(function () {
      $('#sidebarCollapse').on('click', function () {
      //  $('#sidebar').toggleClass('active');
      });
    });
    
      if (this.tokenStorage.getToken()) {
        this.isLoggedIn = true;
        $("#parent_body").removeClass("login_img");
          this.router.navigateByUrl(this.normalizeUrl(this.authService.redirectUrl || '/welcome'));
      }else{
      $("#parent_body").addClass("login_img");
      this.router.navigate(['/login']);
      }
  }
  // getting email and password
  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }

  onSubmit() {
    // login service
    this.spinner.show();
     this.authService.login(this.email?.value,this.password?.value).subscribe((data)=>{
       this.spinner.hide();
       $("#parent_body").removeClass("login_img");
       this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
   this.router.navigate(['/welcome']);

     },error=>{
       this.spinner.hide();
       this.isLoginFailed = true;
       this.loginFailedMsg = '';
       if (error.status == 405){
        this.loginFailedMsg = "Current User is blocked. Please contact Adminstrator"
      }else{
        this.loginFailedMsg = "Email and password you entered don't match our records."
      }       
       
     });
    
  }
	backLogin(){
		$('.form-group').css('margin-top', '15px');
		$('.textRed ,.textRed1,.textGreen').hide();
		$('#passwordReset').slideUp();
		$('#loginTenant').slideDown("slow");
    this.isPasswordResetChecked= false;
	}
  showPwd(){
    var $pwd = $("#pass");
		    if ($pwd.attr('type') === 'password') {
		        $pwd.attr('type', 'text');
		    } else {
		        $pwd.attr('type', 'password');
		    }
  }
  onReset(){
    this.resetErrorMsg = this.resetMsg = "";
    
    if (this.resetForm.valid){
      var userPwdDto: UserPwdReset={
        "userEmail": this.resetForm.controls["userEmail"].value,
        "url": window.origin +"/password-reset"
      };
      this.spinner.show();
      this.userSrv.resetUserCrendentials(userPwdDto).subscribe((res)=>{
          if(res != null &&  res.code == "200"){
            this.resetMsg = res?.text ?? '';
            this.resetErrorMsg="";
            this.isEmailSent=true;
          }
          else if (res?.status == 405){
            this.resetErrorMsg = res?.text ?? '';
            this.resetMsg = "";
            this.isEmailSent = false;
          }
          else{
            this.resetErrorMsg = res?.text ?? '';
            this.resetMsg="";
            this.isEmailSent=false;
            
          }
        this.spinner.hide();
      },_error=>{
        this.resetErrorMsg = "Email not sent. Please contact support";
        this.resetMsg = "";
        this.isEmailSent = false;
        this.spinner.hide()
      });
    }
  }

  private normalizeUrl(url: string): string {
    const rawUrl = String(url || '').trim();
    if (!rawUrl) {
      return '/welcome';
    }
    const [pathPart, queryPart] = rawUrl.split('?');
    let normalizedPath = pathPart;
    try {
      normalizedPath = decodeURIComponent(normalizedPath);
    } catch {
      normalizedPath = pathPart;
    }
    normalizedPath = normalizedPath.replace(/\\/g, '/').replace(/\/+/g, '/').replace(/^\/+/g, '').replace(/\/+$/g, '');
    if (!normalizedPath) {
      return '/welcome';
    }
    return `/${normalizedPath}${queryPart ? `?${queryPart}` : ''}`;
  }
}
