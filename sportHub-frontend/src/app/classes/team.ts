export class Team{
  name!: string
  location!: string
  addedAt!: string
  category!: string
  subCategory!: string
  longitude!: number
  latitude!: number


  constructor(name: string, location: string, addedAt: string, category: string, subCategory: string){
    this.name = name
    this.location = location
    this.addedAt = addedAt
    this.category = category
    this.subCategory = subCategory
  }
}
