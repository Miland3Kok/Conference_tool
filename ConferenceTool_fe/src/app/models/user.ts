import {Role} from "./role";
import {SafeUrl} from "@angular/platform-browser";

export type User = {
      user_id : number;
      firstName : string;
      lastName : string;
      office_function : string;
      roles : Role[];
      givenProfilePicture : SafeUrl | undefined;
}