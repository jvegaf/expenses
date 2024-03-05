import { Group } from "../groups/group.model"
import { User } from "../users/user.model"

export interface Expense {
  id: number
  payer: User
  group: Group
  name: string
  amount: number
  paymentDate: Date
}
