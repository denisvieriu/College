(defun getRightSubtree(L cont)
  (cond
   ((zerop cont)    L)
   (T            (getRightSubtree (rest (rest L)) (+ (1- cont) (second L))))
  )
) 

(defun inorderTraversal(L)
  (cond
   ((= (second L) 0)        (list (first L)))
   ((= (first (rest L)) 1)  (cons(third L) (cons (first L) nil)))
   (T                       (append (inorderTraversal (rest (rest L))) (append (list (first L)) (inorderTraversal (getRightSubtree (rest (rest L)) 1)))))
  )
)

(defvar *list* '(A 2 B 0 C 2 D 0 E 0))
(setq listTree (getRightSubtree (rest (rest *list*)) 1))
(setq listr (inorderTraversal *list*))
(print listr) 


(defun testInorder()
  (assert (equal (inorderTraversal *list*) '(B A D C E)))
  (assert (equal (inorderTraversal '(A 2 B 2 C 1 D 0 E 0 F 2 G 0 H 0)) '(D C B E A G F H))) 
)

(testInorder)