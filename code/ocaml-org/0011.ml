(* Modified Run-Length Encoding *)
(* Level: Beginner *)

(* Modify the result of the previous problem in such a way that if an element has no duplicates it is simply copied into the result list. Only elements with duplicates are transferred as (N E) lists. *)
(* Since OCaml lists are homogeneous, one needs to define a type to hold both single elements and sub-lists. *)

(* Example: *)
(* # encode ["a"; "a"; "a"; "a"; "b"; "c"; "c"; "a"; "a"; "d"; "e"; "e"; "e"; "e"];; *)
(* - : string rle list = *)
(* [Many (4, "a"); One "b"; Many (2, "c"); Many (2, "a"); One "d"; *)
(*  Many (4, "e")] *)

type 'a rle =
  | One of 'a
  | Many of int * 'a

let rec encode l =
  let create_rle n x =
    if n = 1 then One x
    else Many (n, x)
  in

  let rec encode n acc = function
    | [] -> []
    | [x] -> (create_rle (n + 1) x) :: acc
    | a :: (b :: _ as t) -> begin
        if a = b then encode (n + 1) acc t
        else encode 0 (create_rle (n + 1) a) :: acc) t
      end
  in List.rev (encode 0 [] l);;
