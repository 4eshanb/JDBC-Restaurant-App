CREATE OR REPLACE FUNCTION reduceSomeCostsFunction( maxVisitCount INT)
    RETURNS INT AS $$
    DECLARE v_visitid INT;
    
            v_counter INTEGER := 0;
            c1 CURSOR FOR
                SELECT v.visitid
                FROM Customer C, Visit V
                WHERE C.custID = V.custID
                AND C.status= 'H' 
                and v.cost IS NOT NULL
                ORDER BY C.joinDate;

            c2 CURSOR FOR
                SELECT  v.visitid
                FROM Customer C, Visit V
                WHERE C.custID = V.custID
                AND C.status= 'M'  
                and v.cost IS NOT NULL
                ORDER BY C.joinDate;

            c3 CURSOR FOR
                SELECT  v.visitid
                FROM Customer C, Visit V
                WHERE C.custID = V.custID
                AND C.status= 'L'  
                and v.cost IS NOT NULL
                ORDER BY C.joinDate;

BEGIN
    OPEN c1;
   
        --EXIT WHEN counter = maxVisitCount;
    WHILE(v_counter <= maxVisitCount)
    LOOP
        FETCH c1 INTO  v_visitid;
        EXIT WHEN NOT FOUND;
        EXIT WHEN v_counter = maxVisitCount;
        
        UPDATE visit 
        SET COST =  COST - .10 * COST
        WHERE  visitid = v_visitid ;
        v_counter := v_counter + 1 ;
    END LOOP;
    close c1;
  
    open c2;
    WHILE(v_counter <= maxVisitCount)
    LOOP
        FETCH c2 INTO  v_visitid;
          EXIT WHEN NOT FOUND;
          EXIT WHEN v_counter = maxVisitCount;
        
            UPDATE visit 
            SET  COST =  COST - .05 * COST
            WHERE  visitid = v_visitid ;
          v_counter := v_counter + 1 ;
    END LOOP;

   close c2;
   open c3;

    WHILE(v_counter <= maxVisitCount)
    LOOP
        FETCH c3 INTO  v_visitid;
        EXIT WHEN NOT FOUND;
        EXIT WHEN v_counter = maxVisitCount;
            
            UPDATE visit 
            SET  COST =  COST - .01 * COST
            WHERE  visitid = v_visitid ;

          v_counter := v_counter + 1 ;
    END LOOP;
    close c3;
    RETURN v_counter;
        
  

END;
$$ LANGUAGE plpgsql;


