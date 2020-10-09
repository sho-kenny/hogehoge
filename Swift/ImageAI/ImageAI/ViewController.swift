//
//  ViewController.swift
//  ImageAI
//
//  Created by kenny on 2020/10/01.
//

import UIKit
import CoreML
import Vision
import AVFoundation

class ViewController: UIViewController, UINavigationControllerDelegate, UIImagePickerControllerDelegate {

    @IBOutlet weak var photoDisplay: UIImageView!
    
    @IBOutlet weak var photoInfoDisplay: UITextView!
    
    var imagePicker: UIImagePickerController!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        imagePicker = UIImagePickerController()
        imagePicker.delegate = self
        imagePicker.sourceType = .camera
        // Do any additional setup after loading the view.
    }

    @IBAction func takePhoto(_ sender: Any) {
        present(imagePicker, animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        photoDisplay.image = info[UIImagePickerController.InfoKey.originalImage] as? UIImage
        imagePicker.dismiss(animated: true, completion: nil)
        imageInferrence(image: (info[UIImagePickerController.InfoKey.originalImage] as? UIImage)!)
    }
    
    func imageInferrence(image: UIImage) {
        // モデルを定義
        guard let model = try? VNCoreMLModel(for: Resnet50().model) else    {
            fatalError("モデルをロードできません")
        }
        // モデルをもとにリクエスト生成（実際のリクエストはハンドラーで行う）
        let request = VNCoreMLRequest(model: model) {
            [weak self] request, error in
            // 判定結果の検証
            guard let results = request.results as? [VNClassificationObservation],
                  let firstResult = results.first else {
                fatalError("判定をできません")
            }
            // 判定結果の説明表示
            DispatchQueue.main.async {
                self?.photoInfoDisplay.text = "Accuracy\(Int(firstResult.confidence * 100))%, \n\n Text Label \((firstResult.identifier))"
                
                let utterWords = AVSpeechUtterance(string:(self?.photoInfoDisplay.text)!)
                utterWords.voice = AVSpeechSynthesisVoice(language: "en-us")
                let synthesizer = AVSpeechSynthesizer()
                synthesizer.speak(utterWords)
            }
        }
        
        guard let ciImage = CIImage(image: image) else {
            fatalError("画像を変換できません")
        }
        // 画像判定のためのリクエストハンドラー生成。引数は判定対象の画像
        let imageHandler = VNImageRequestHandler(ciImage: ciImage)
        // 画像とモデルのマッチングを行い、リクエストを実行
        DispatchQueue.global(qos: .userInteractive).async {
            do {
                try imageHandler.perform([request])
            } catch {
                print("エラー \(error)")
            }
        }
    }
    
}

