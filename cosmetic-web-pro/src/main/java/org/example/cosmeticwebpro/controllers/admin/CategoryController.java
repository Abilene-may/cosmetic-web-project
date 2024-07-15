package org.example.cosmeticwebpro.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.domains.Category;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.CategoryReqDTO;
import org.example.cosmeticwebpro.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {
  private final CategoryService categoryService;

  /**
   * Create a new category
   */
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody CategoryReqDTO reqDTO){
    try{
      categoryService.create(reqDTO.getCategoryName());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * update a category
   */
  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody Category category){
    try{
      categoryService.update(category);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * find all brands
   */
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll(){
    try{
      var categories = categoryService.getAll();
      return new ResponseEntity<>(categories, HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Get a category by id
   */
  @GetMapping("/{categoryId}")
  public ResponseEntity<Object> gteById(@PathVariable Long categoryId){
    try{
      var category = categoryService.getById(categoryId);
      return new ResponseEntity<>(category, HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Get a brand by id
   */
  @DeleteMapping("/delete/{categoryId}")
  public ResponseEntity<Object> delete(@PathVariable Long categoryId){
    try{
      categoryService.delete(categoryId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
