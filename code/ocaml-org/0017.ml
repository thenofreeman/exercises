(* Split a List Into Two Parts; The Length of the First Part Is Given *)
(* Level: Beginner *)

(* Split a list into two parts; the length of the first part is given. *)
(* If the length of the first part is longer than the entire list, then the first part is the list and the second part is empty. *)

(* Example: *)
(* # split ["a"; "b"; "c"; "d"; "e"; "f"; "g"; "h"; "i"; "j"] 3;; *)
(* - : string list * string list = *)
(* (["a"; "b"; "c"], ["d"; "e"; "f"; "g"; "h"; "i"; "j"]) *)
(* # split ["a"; "b"; "c"; "d"] 5;; *)
(* - : string list * string list = (["a"; "b"; "c"; "d"], []) *)

let split l n =
  let create_pair l r = (List.rev l, r) in

  let rec split n left right =
    match right with
      | [] -> create_pair left right
      | hd :: tl ->
        if n = 0 then (create_pair left right)
        else split (n - 1) (hd :: left) tl
  in split n [] l;;
