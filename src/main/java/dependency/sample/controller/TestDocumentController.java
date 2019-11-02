package dependency.sample.controller;

import dependency.sample.entity.TestDocument;
import dependency.sample.repository.TestDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test_document")
public class TestDocumentController {

    private final TestDocumentRepository testDocumentRepository;

    @Autowired
    public TestDocumentController(TestDocumentRepository testDocumentRepository) {
        this.testDocumentRepository = testDocumentRepository;
    }

    @PostMapping
    public ResponseEntity<TestDocument> create(@RequestBody TestDocument testDocument) {
        return new ResponseEntity<>(testDocumentRepository.save(testDocument), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<TestDocument>> findAll() {
        return new ResponseEntity<>(testDocumentRepository.findAll(), HttpStatus.OK);
    }
}
