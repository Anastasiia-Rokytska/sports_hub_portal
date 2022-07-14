export class User {
  id!: number;
  firstName!: string;
  lastName!: string;
  photo!: string;

  constructor(id: number, firstName: string, lastName: string, photo: string) {
    this.id = id
    this.firstName = firstName
    this.lastName = lastName
    this.photo = photo
    if(photo == null){
      this.photo = "../../assets/images/userPhoto.jpg";
    }
  }
  printInfo() {
    console.log("id : " + this.id)
    console.log("firstName : " + this.firstName)
    console.log("lastName : " + this.lastName)
    console.log("photo : " + this.photo)
  }
}

