import { formatDate } from "@angular/common"

export class Team{
  name!: string
  location!: string
  addedAt!: string
  category!: string
  subCategory!: string
  longitude!: number
  latitude!: number
  image: string | null = null


  constructor(name: string, location: string, addedAt: string, category: string,
    subCategory: string, longitude: number, latitude: number, image: string | null){
    this.name = name
    this.location = location
    this.addedAt = formatDate(addedAt, 'dd/MM/yyyy', 'en-US')
    this.category = category
    this.subCategory = subCategory
    this.longitude = longitude
    this.latitude = latitude
    this.image = image
  }
}
