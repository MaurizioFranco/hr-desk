import React, { Component } from "react";
import centauri_logo from "../images/header_logo.png";
import "./LoginView.css";
import * as Commons from "../commons.js";
import * as Constants from "../constants.js";
import { ModalLoadingSpinnerComponent } from "./loader/ModalLoadingSpinnerComponent";
import LoginAuthenticationKOMessage from "./login/LoginAuthenticationKOMessage.js";
import Registration from "./RegistrationView";
import InfoIcon from '@mui/icons-material/Info';

import { styled } from '@mui/material/styles';
import Button from '@mui/material/Button';
import Tooltip, { TooltipProps, tooltipClasses } from '@mui/material/Tooltip';

class LoginView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      password_input_type: "password",
      id: "",
      formEmail: "",
      psw: "",
      authenticationKO: false,
      user: [],
      applicationInfoData: null
    };
    sessionStorage.clear();
    this.retrieveApplicationInfo();
  }

  retrieveApplicationInfo = () => {
		Commons.executeFetchWithHeader(
			Constants.FULL_APPLICATION_INFO_API,
			"GET",
			{ 'Content-Type': 'application/json' },
			this.setApplicationInfo
		  );
	}

	setApplicationInfo = (responseData) => {
     sessionStorage.setItem("applicationInfo", JSON.stringify(responseData));
     this.setState({
      applicationInfoData: responseData
     });
	}


  formSubmit(event) {
    event.preventDefault();
    this.getUserByEmail();
  }

  getUserByEmail = () => {
    console.log("getUserByEmail - START");
    let headerToken = Commons.getAuthorizationHeader(
      this.state.formEmail,
      this.state.psw
    );

    Commons.debugMessage(
      "getUserByEmail - DEBUG - FULL_GET_USER_BY_EMAIL_API: " +
      Constants.FULL_GET_USER_BY_EMAIL_API
    );

    Commons.executeFetchWithHeader(
      Constants.FULL_GET_USER_BY_EMAIL_API + this.state.formEmail,
      "GET",
      headerToken,
      this.setUserData,
      this.showAuthenticationError
    );
  }

  checkCredentials = () => {

    let headerToken = Commons.getAuthorizationHeader(
      this.state.formEmail,
      this.state.psw
    );

    Commons.debugMessage(
      "LoginView.checkCredentials - START - FULL_API_URI: " +
      Constants.FULL_API_URI
    );

    if (this.state.user.enabled === false) {
      Commons.operationError({ errorMessage: "Your account is not enabled. Check your emails to enable it" });
    }

    else {
      Commons.executeFetchWithHeader(
        Constants.FULL_API_URI,
        "GET",
        headerToken,
        this.goAhead,
        this.showAuthenticationError
      );
    }
  };

  goAhead = (responseData) => {
    //		console.log("LoginView.goAhead - START");
    //		console.log(responseData);
    //		console.log(responseData.principal.id);
    sessionStorage.setItem("userLoggedEmail", this.state.formEmail);
    sessionStorage.setItem("userId", responseData.principal.id);
    sessionStorage.setItem("headerToken",
      Commons.getAuthorizationToken(this.state.formEmail, this.state.psw)
    );
    let queryParams = new URLSearchParams(this.props.location.search);
    let targetPage = queryParams.get('targetPage');

    if (targetPage) {
      // Se c'è un valore targetPage, reindirizza l'utente alla pagina corrispondente dopo il login
      this.props.history.push(`/${targetPage}`);
    } else {
      // Altrimenti, reindirizza l'utente alla homepage dopo il login
      this.props.history.push('/');
    }
    // this.props.history.push("/");
  };

  setUserData = (responseData) => {
    this.setState({ user: responseData });
    sessionStorage.setItem("user", JSON.stringify(responseData));
    this.checkCredentials();
  }

  showAuthenticationError = () => {
    this.setState({ authenticationKO: true });
  };

  handleChange = (event) => {
    const input = event.target;
    const value = input.type === "checkbox" ? input.checked : input.value;
    this.setState({ [input.name]: value });
  };

  toggleShowPassword = () => {
      console.log("toggleShowPassword...");
      var password_input_type = this.state.password_input_type ;
      if (password_input_type === "password") {
        this.setState({password_input_type: "text"});
      } else {
        this.setState({password_input_type: "password"});
      }
  }

  render() {
    // sessionStorage.setItem("applicationInfo", responseData);
    //const applicationInfoData = JSON.parse(sessionStorage.getItem("applicationInfo"));
    // console.log("applicationInfoData: ----->");
    // console.log(applicationInfoData);
    // console.log(JSON.stringify(applicationInfoData));
    // console.log(JSON.parse(applicationInfoData));
    const infoTooltipedIcon =
      ((this.state.applicationInfoData !== null) ?
        (<Tooltip title={"Versione backend: " + this.state.applicationInfoData.backendVersion}>
          <Button sx={{ m: 1 }}><InfoIcon style={{ color: '#000', fontSize: "medium", margin: "0px", padding: "0px", minWIdth: "0px" }} /></Button>
        </Tooltip>)
        :
        '');
    // const infoTooltipedIcon = ((applicationInfoData!==null&&applicationInfoData!=undefined)?(<Tooltip title={"Versione backend: " + JSON.stringify(applicationInfoData)}>
    //   <Button sx={{ m: 1 }}><InfoIcon style={{color:'#000',fontSize:"medium", margin: "0px", padding: "0px", minWIdth: "0px"}}/></Button>
    // </Tooltip>):'');

    return (
      <div className="container-fluid ">
        <ModalLoadingSpinnerComponent />
        <div id="login-view-main-container">
          <div className="product-info">
            <div className="login-main-text">
              <img src={centauri_logo} alt="logo" className="login_logo" />
              <span className="title">HrDesk</span>

              {infoTooltipedIcon}

            </div>
          </div>
          <div className="login-form">
            <div className="col-md-6 col-sm-12">
              <LoginAuthenticationKOMessage
                visibility={this.state.authenticationKO}
              />
              <label>Inserisci le credenziali per il login oppure registra una nuova utenza</label>
              <form onSubmit={this.formSubmit.bind(this)}>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control"
                    name="formEmail"
                    value={this.state.formEmail}
                    onChange={this.handleChange}
                    placeholder="email"
                    required
                  />
                </div>
                <div className="form-group">
                  <input
                    type={this.state.password_input_type}
                    className="form-control"
                    name="psw"
                    value={this.state.psw}
                    onChange={this.handleChange}
                    placeholder="password"
                    required
                  />
                </div>
                <div>
                    <label for="show_password_checkbox">Visualizza password</label>
                    <input type="checkbox" id="show_password_checkbox" onClick={this.toggleShowPassword} />
                </div>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                  <input type="submit" className="btn btn-black" value="ENTRA" />
                  <Registration />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default LoginView;
