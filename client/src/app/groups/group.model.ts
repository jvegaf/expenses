import { Expense } from "../expenses/expense.model";
import { User } from "../users/user.model";

export interface Group {
  id: number;
  name: string;
  members?: User[];
  expenses?: Expense[];
}
